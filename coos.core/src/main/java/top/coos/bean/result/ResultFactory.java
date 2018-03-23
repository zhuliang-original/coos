package top.coos.bean.result;

import top.coos.exception.CoreException;

public class ResultFactory {

    public enum ResultType {
        //
        ONE(1, ResultOne.class),
        //
        LIST(2, ResultList.class),
        //
        PAGELIST(3, ResultPageList.class);

        private ResultType(int code, Class<?> beanClass) {
            this.code = code;
            this.beanClass = beanClass;
        }

        private int code;

        private Class<?> beanClass;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Class<?> getBeanClass() {
            return beanClass;
        }

        public void setBeanClass(Class<?> beanClass) {
            this.beanClass = beanClass;
        }

    }

    private ResultFactory() {

    }

    public static <T> T getResult(ResultType type) throws CoreException {
        try {

            @SuppressWarnings("unchecked")
            T newInstance = (T) type.getBeanClass().newInstance();
            return newInstance;
        } catch (Exception e) {
            throw new CoreException(e);
        }
    }
}
