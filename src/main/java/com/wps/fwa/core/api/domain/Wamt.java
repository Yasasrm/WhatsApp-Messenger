package com.wps.fwa.core.api.domain;


import lombok.Data;

import javax.persistence.*;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Entity
@Table(name = "Wamt")
@Data
@NamedQuery(name = "wamt.findTemplate", query = "SELECT t FROM Wamt t WHERE t.wamtTemplate = ?1")
public class Wamt {

    @Id
    @Column(name = "Wamt_Template", length = 30)
    private String wamtTemplate;

    @Column(name = "Wamt_Para_Count", nullable = false)
    private Integer wamtParaCount;

    @Column(name = "Wamt_Msg_Code", length = 4, nullable = false)
    private String wamtMsgCode;

    @Column(name = "Wamt_Sample", length = 4000, nullable = false)
    private String wamtSample;

    @Column(name = "Wamt_Para_Deff", length = 4000)
    private String wamtParaDeff;

}
