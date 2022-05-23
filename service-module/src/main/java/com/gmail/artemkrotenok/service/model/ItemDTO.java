package com.gmail.artemkrotenok.service.model;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {

    private Long id;
    @NotNull(message = "The field 'name' must be filled")
    @Size(min = 1, max = 40, message = "'description' size must be between 1 and 40 characters")
    private String name;
    private String uniqueNumber;
    @NotNull(message = "The field 'description' must be filled")
    @DecimalMax("1000000000")
    private BigDecimal price;
    @NotNull(message = "The field 'description' must be filled")
    @Size(min = 1, max = 200, message = "'description' size must be between 1 and 200 characters")
    private String description;
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
