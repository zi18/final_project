package com.ironhackproject.bank.dto;

import com.ironhackproject.bank.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private Integer id;
    private String name;
    private LocalDateTime dateOfBirth;
    private AddressDTO primaryAddressDTO;
    private AddressDTO mailingAddressDTO;
    private String hashedKey;
}
