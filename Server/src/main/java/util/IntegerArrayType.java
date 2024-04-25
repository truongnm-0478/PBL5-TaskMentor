package util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerArrayType implements UserType {

    private static final int[] SQL_TYPES = {Types.ARRAY};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<Integer[]> returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1 || (o != null && o1 != null && o.equals(o1));
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o != null ? o.hashCode() : 0;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object o) throws HibernateException, SQLException {
        Array array = resultSet.getArray(names[0]);
        if (array == null) return null;
        Integer[] integers = (Integer[]) array.getArray();
        return integers;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(index, SQL_TYPES[0]);
        } else {
            Integer[] castObject = (Integer[]) o;
            Array array = session.connection().createArrayOf("integer", castObject);
            preparedStatement.setArray(index, array);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) return null;
        Integer[] copy = new Integer[((Integer[]) o).length];
        System.arraycopy(o, 0, copy, 0, copy.length);
        return copy;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object original, Object target, Object o1) throws HibernateException {
        return original;
    }
}

