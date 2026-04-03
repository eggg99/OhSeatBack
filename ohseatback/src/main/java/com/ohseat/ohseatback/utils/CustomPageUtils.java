package com.ohseat.ohseatback.utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class CustomPageUtils {

    public static Pageable getPageable(int page, int size, List<String> sortList) {
        List<Sort.Order> orderList = new ArrayList<>();

        if (sortList.size() == 2 && !sortList.get(0).contains(",")) { // id,desc 한 개로만 이루어짐
            String property = sortList.get(0);
            Sort.Direction direction = Sort.Direction.fromString(sortList.get(1));
            orderList.add(new Sort.Order(direction, property));
            return PageRequest.of(page, size, Sort.by(orderList));
        }

        for (String sortStr : sortList) {
            String[] split = sortStr.split(",");
            String property = split[0];
            String direction = (split.length == 2) ? split[1] : "asc";

            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            orderList.add(new Sort.Order(sortDirection, property));
        }

        return PageRequest.of(page, size, Sort.by(orderList));
    }

    public static Pageable getPageable(int page, int size, String sort) {
        return PageRequest.of(Math.max(page, 0), size, Sort.by(Sort.Direction.fromString(sort), "id"));
    }

    public static Pageable getPageable(int page, int size, String sort, String sortColumn) {
        return PageRequest.of(Math.max(page, 0), size, Sort.by(Sort.Direction.fromString(sort), sortColumn));
    }

    public static Pageable getPageable(int page, int size) {
        return PageRequest.of(Math.max(page, 0), size, Sort.by(Sort.Direction.fromString("DESC"), "id"));
    }

    public static String makeOrderBy(Pageable pageable, Map<String, String> aliasMap) {
        String orderBy = String.join(", ",
                pageable.getSort().stream().map(order -> {
                    String alias = aliasMap.containsKey(order.getProperty())
                            ? aliasMap.get(order.getProperty()) + "."
                            : "";
                    return alias + order.getProperty() + " " + order.getDirection().name();
                }).toList()
        );
        return "ORDER BY " + orderBy;
    }

    public static String getDefaultSort(Pageable pageable) {
        return Objects.requireNonNull(pageable.getSort().getOrderFor("id")).getDirection().name();
    }

}
