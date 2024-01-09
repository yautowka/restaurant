package com.example.restaurant.filter;

import java.util.List;

public class JsonIncludeRestaurantsFilter {
    @Override
    public boolean equals(Object other) {
        if (other instanceof List<?>) {
            List<Integer> temp = (List<Integer>) other;
            return temp.isEmpty();
        }
        return true;
    }
}
