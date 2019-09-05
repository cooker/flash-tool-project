package org.grant.utils.invoice;

import java.util.Arrays;

public enum InvoiceType {
    PLAIN_INVOICE("增值税电子普通发票", "0");

    String name;
    String type;
    InvoiceType(String name, String type){
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static InvoiceType getByName(String name){
        return Arrays.stream(InvoiceType.values()).filter((n)-> n.name.equals(name) ).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "InvoiceType{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
