/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.generator.standard.manager;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Method;
import static com.speedment.generator.standard.internal.util.GenerateMethodBodyUtil.generateFields;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import java.lang.reflect.Type;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since  2.3.0
 */
public final class GeneratedManagerImplTranslator 
        extends AbstractEntityAndManagerTranslator<Class> {

    public final static String 
        ENTITY_COPY_METHOD_NAME    = "entityCopy",
        ENTITY_CREATE_METHOD_NAME  = "entityCreate",
        FIELDS_METHOD              = "fields",
        PRIMARY_KEYS_FIELDS_METHOD = "primaryKeyFields";

    public GeneratedManagerImplTranslator(Table table) {
        super(table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {

        return newBuilder(file, getSupport().generatedManagerImplName())
            /**
             * The table specific methods.
             */
            .forEveryTable((clazz, table) -> {
                clazz
                    .public_()
                    .abstract_()
                    .setSupertype(SimpleParameterizedType.create(
                        AbstractManager.class,
                        getSupport().entityType()
                    ))
                    .add(getSupport().generatedManagerType())
                    .add(Field.of("tableIdentifier", SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType())).private_().final_())
                    .add(Constructor.of().protected_()
                        .add("this.tableIdentifier = " + TableIdentifier.class.getSimpleName() + ".of("
                            + Stream.of(getSupport().dbmsOrThrow().getId(), getSupport().schemaOrThrow().getId(), getSupport().tableOrThrow().getId())
                            .map(s -> "\"" + s + "\"").collect(joining(", ")) 
                            + ");")
                    )
                    .add(Method.of("getTableIdentifier", SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType()))
                        .public_().add(OVERRIDE)
                        .add("return tableIdentifier;")
                    )
                    .add(generateFields(getSupport(), file, FIELDS_METHOD, table::columns))
                    .add(generateFields(getSupport(), file, PRIMARY_KEYS_FIELDS_METHOD,
                        () -> table.columns().filter(GeneratedManagerImplTranslator::isPrimaryKey))
                    )
                    ;
            })
            .build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base implementation for the manager of every {@link "
            + getSupport().entityType().getTypeName() + "} entity.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedManagerImplName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    public Type getImplType() {
        return getSupport().managerImplType();
    }

    private static boolean isPrimaryKey(Column column) {
        return column.getParentOrThrow().findPrimaryKeyColumn(column.getId()).isPresent();
    }
}