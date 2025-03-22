package com.tcc.rag_open_ai_tcc.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.sql.internal.DdlTypeImpl;

import java.sql.Types;

public class CustomPostgreSQLDialect extends PostgreSQLDialect {
    public CustomPostgreSQLDialect() {
        super(DatabaseVersion.make(15)); // Use your PostgreSQL version
    }

    @Override
    protected void registerColumnTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.registerColumnTypes(typeContributions, serviceRegistry);

        // Register vector type
        typeContributions.getTypeConfiguration().getDdlTypeRegistry()
                .addDescriptor(new DdlTypeImpl(Types.OTHER, "vector", this));
    }
}