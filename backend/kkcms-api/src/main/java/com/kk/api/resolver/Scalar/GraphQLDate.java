package com.kk.api.resolver.Scalar;

import graphql.language.ScalarTypeDefinition;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by msi- on 2018/2/12.
 */
public class GraphQLDate extends GraphQLScalarType {

    public GraphQLDate() {
        super("DateTime", "DateTime type", new Coercing<Date, String>() {
            private Date convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = DateTimeHelper.parseDate((String) input);
                    if (localDateTime != null) {
                        return DateTimeHelper.toDate(localDateTime);
                    }
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof Date) {
                    return DateTimeHelper.toISOString((Date) input);
                } else {
                    Date result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for Date");
                    }
                    return DateTimeHelper.toISOString(result);
                }
            }

            @Override
            public Date parseValue(Object input) {
                Date result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for Date");
                }
                return result;
            }

            @Override
            public Date parseLiteral(Object input) {
                if (!(input instanceof StringValue)) return null;
                String value = ((StringValue) input).getValue();
                Date result = convertImpl(value);
                return result;
            }
        }, new ScalarTypeDefinition("DateTime"));
    }
}
