package com.tfkfan.exception;

import com.tfkfan.webservices.categoryservice.BaseFault;

/**
 * @author Baltser Artem tfkfan
 */
public interface SoapFaultConvertable {
    BaseFault convert();
}
