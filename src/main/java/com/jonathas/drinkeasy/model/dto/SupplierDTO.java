package com.jonathas.drinkeasy.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private UUID id;
    private String name;
    private String cnpj;
    private String phone;
    private String address;

    
}
