package com.odde;

import java.util.List;

public class OrdersWriter {
    private Orders orders;

    public OrdersWriter(Orders orders) {
        this.orders = orders;
    }

    private String createJSONItem(String key, String value) {
        return "\"" + key + "\": \"" + value + "\"";
    }

    private String createJSONItem(String key, Double value) {
        return "\"" + key + "\": " + value;
    }

    private String createJSONItem(String key, int value) {
        return "\"" + key + "\": " + value;
    }

    private StringBuffer createProduct(Product product) {
        StringBuffer sb = new StringBuffer();

        sb.append("{");
        sb.append(createJSONItem("code", product.getCode()));
        sb.append(", ");
        sb.append(createJSONItem("color", product.getColor()));
        sb.append(", ");

        String size = product.getSize();
        if (!Product.SIZE_NOT_APPLICABLE.equals(size)) {
            sb.append(createJSONItem("size", size));
            sb.append(", ");
        }

        sb.append(createJSONItem("price", product.getPrice()));
        sb.append(", ");
        sb.append(createJSONItem("currency", product.getCurrency()));
        sb.append("}, ");

        return sb;
    }

    private void deleteComma(StringBuffer sb) {
        sb.delete(sb.length() - 2, sb.length());
    }

    public String getContents() {
        StringBuffer sb = new StringBuffer("{\"orders\": [");
        List<Order> orderList = orders.getOrders();

        for (Order order : orderList) {
            sb.append("{");
            sb.append(createJSONItem("id", order.getOrderId()));
            sb.append(", ");
            sb.append("\"products\": [");

            for (Product product : order.getProducts()) {
                sb.append(createProduct(product));
            }

            if (order.getProductsCount() > 0) {
                deleteComma(sb);
            }
            sb.append("]}, ");
        }

        if (orderList.size() > 0) {
            deleteComma(sb);
        }

        return sb.append("]}").toString();
    }
}