package com.devthiagofurtado.data.vo;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponseVO extends ResourceSupport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;

}
