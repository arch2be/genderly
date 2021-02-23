package io.github.arch2be.genderly.gender.mocked_db_rules;

import io.github.arch2be.genderly.gender.GenderType;

class GenderSearchPageable {
    private GenderType genderType;
    private Integer pageNumber;
    private Integer pageSize;

    public GenderSearchPageable(GenderType genderType, Integer pageNumber, Integer pageSize) {
        this.genderType = genderType;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
