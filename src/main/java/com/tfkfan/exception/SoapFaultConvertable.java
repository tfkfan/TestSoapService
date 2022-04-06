package com.tfkfan.exception;

import com.tfkfan.webservices.types.BaseFault;

/**
 * @author Baltser Artem tfkfan
 */
public interface SoapFaultConvertable {
    BaseFault convert();
}
