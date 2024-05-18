package com.wps.fwa.core.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Entity
@Table(name = "Wamq")
@Data
@NamedQueries(
        {
                @NamedQuery(name = "wamq.findMessage", query = "SELECT q FROM Wamq q WHERE q.wamqMsgId = ?1"),
                @NamedQuery(name = "wamq.findBulk", query = "SELECT q.wamqMsgId FROM Wamq q WHERE q.wamqBatchId = ?1"),
                @NamedQuery(name = "wamq.deleteMessage", query = "DELETE FROM Wamq q WHERE q.wamqMsgId = ?1"),
                @NamedQuery(name = "wamq.deleteBulk", query = "DELETE FROM Wamq q WHERE q.wamqBatchId = ?1")
        }
)
public class Wamq {

    @Id
    @Column(name = "Wamq_Msg_Id", length = 20)
    private String wamqMsgId;

    @Column(name = "Wamq_To", length = 13)
    private String wamqTo;

    @Column(name = "Wamq_Template", length = 30)
    private String wamqTemplate;

    @Column(name = "Wamq_Para_List", length = 1000)
    private String wamqParaList;

    @Column(name = "Wamq_Batch_Id")
    private String wamqBatchId;

    @Column(name = "Wamq_C_Date")
    private Date wamqCDate;

    @Column(name = "Wamq_C_User", length = 10)
    private String wamqCUser;

}
