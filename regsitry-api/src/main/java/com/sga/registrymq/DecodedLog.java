package com.sga.registrymq;

import lombok.AllArgsConstructor;

/**
 * Created by navee on 12/10/2016.
 */

@AllArgsConstructor()
public class DecodedLog {
    private String email;
    private long processNumber;
    private String process;
    private String Status;

}
