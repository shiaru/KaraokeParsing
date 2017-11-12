package org.karaoke.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Argument {
    private Company company;
    private Category category;
    private String word;

    public Company getCompany() {
        return company;
    }

    public Argument setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Argument setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getWord() {
        return word;
    }

    public Argument setWord(String word) {
        this.word = word;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}