package com.seongmin.fileupload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FileDTO {

    private String originFileName;
    private String saveName;
    private String filePath;
    private String fileDescription;
}
