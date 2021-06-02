package com.example.currencyconverter.constants;

public class Constants {


    public enum Fields {

        SOURCE_CURRENCY("query.from"),
        TARGET_CURRENCY("query.to"),
        SUCCESS("success"),
        RESULT("result"),
        AMOUNT("query.amount");

        private final String value;

        Fields(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
