package com.example.citronix.web.VM;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class ArbreVM {

    @NotNull(message = "in the champ")
    private LocalDateTime date_de_plantation;


    @NotNull(message = "in the champ")
    private UUID champ_id;

}
