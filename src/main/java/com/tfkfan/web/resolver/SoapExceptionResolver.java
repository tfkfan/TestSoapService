package com.tfkfan.web.resolver;


import com.tfkfan.exception.BusinessException;
import com.tfkfan.webservices.categoryservice.BaseFault;
import com.tfkfan.webservices.categoryservice.Fault;
import com.tfkfan.webservices.categoryservice.InternalException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;
/*import org.springframework.oxm.Marshaller;
import org.valid4j.errors.RequireViolation;
import ru.mos.emias.system.v1.faults.*;*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@NoArgsConstructor
public class SoapExceptionResolver implements FaultListener {
    private final static String faultStringOrReason = "Fault occurred while processing.";

  /*  @Qualifier(JaxBConfig.ETD_MARSHALLER)
    @Autowired
    Marshaller marshaller;

    @Autowired
    FaultMapper mapper;*/

    @Override
    public boolean faultOccurred(Exception e, String s, Message message) {
        Throwable ex = convertExceptionIfRequired(ExceptionUtils.getRootCause(e));
        BaseFault businessFault = convert(ex);

        Fault fault =
            new Fault(ex.getMessage(), businessFault);

        message.setContent(Exception.class, fault);

        return true;
    }

    private Throwable convertExceptionIfRequired(Throwable ex) {
        if (ex instanceof IllegalArgumentException && ex.getMessage().toUpperCase()
            .contains("Unable to locate Attribute".toUpperCase())) {
            Pattern pattern = Pattern.compile("\\[([\\w.]*)\\]*");
            Matcher matcher = pattern.matcher(ex.getMessage());
            if (matcher.find()) {
               /* return new com.nord.etd3.core.exception.ValidationAttributeException("Переданы некорректные атрибуты поиска сущностей",
                        new ValidationDetails("attribute", matcher.group(1)));*/
            }
        }
        return ex;
    }

    private BaseFault convert(Throwable ex) {
        if (ex instanceof BusinessException) {
            return ((BusinessException) ex).convert();
        }

        InternalException exception = new InternalException();
        exception.setMessage(ex.getMessage());

        return exception;
    }
}
