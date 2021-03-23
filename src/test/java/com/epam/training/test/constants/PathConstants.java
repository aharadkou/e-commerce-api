package com.epam.training.test.constants;

public interface PathConstants {

    String PAGEABLE_REQUEST_PARAMS = "?page={page}&size={size}";



    String PATH_CATEGORIES = "categories";

    String PATH_CATEGORIES_GET_ALL = PATH_CATEGORIES + PAGEABLE_REQUEST_PARAMS;

    String PATH_CATEGORY_BY_ID = PATH_CATEGORIES + "/{id}";

    String PATH_FIND_CATEGORY_BY_NAME = PATH_CATEGORIES + "/name/{name}";



    String PATH_PRODUCTS = "products";

    String PATH_PRODUCTS_GET_ALL = PATH_PRODUCTS + PAGEABLE_REQUEST_PARAMS;

    String PATH_PRODUCT_BY_ID = PATH_PRODUCTS + "/{id}";

    String PATH_PRODUCT_CATEGORY = PATH_PRODUCTS + "/category/{id}";

    String PATH_PRODUCT_NAME = PATH_PRODUCTS + "/name/{name}";

    String PATH_PRODUCTS_PRICE = PATH_PRODUCTS + "/price?amount={amount}&currency={currency}";

    String PATH_PRODUCTS_PRICE_RANGE =
            PATH_PRODUCTS + "/price/range?from={from}&to={to}&currency={currency}";



    String PATH_PRICE = "products/{id}/price";


    String JSON_PAGE_SIZE = "page.size";

    String JSON_PAGE_TOTAL_ELEMENTS = "page.totalElements";

    String JSON_PAGE_TOTAL_PAGES = "page.totalPages";

    String JSON_CONTENT = "content";



}
