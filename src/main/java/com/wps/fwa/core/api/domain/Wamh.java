package com.wps.fwa.core.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Entity
@Table(name = "Wamh")
@Data
@NamedQueries(
        {
                @NamedQuery(name = "wamh.findMessage", query = "SELECT h1 FROM Wamh h1 WHERE h1.wamhMsgId = ?1 AND h1.wamhId IN (SELECT MAX (h2.wamhId) FROM Wamh h2 WHERE h2.wamhMsgId = h1.wamhMsgId)"),
                @NamedQuery(name = "wamh.findById", query = "SELECT h FROM Wamh h WHERE h.wamhId = ?1"),
                @NamedQuery(name = "wamh.messageCount", query = "SELECT COUNT(h) FROM Wamh h WHERE h.wamhMsgId = ?1")
        }
)
public class Wamh {

    @Id
    @Column(name = "Wamh_Id")
    private Integer wamhId;

    @Column(name = "Wamh_Msg_Id", length = 20)
    private String wamhMsgId;

    @Column(name = "Wamh_To", length = 13)
    private String wamhTo;

    @Column(name = "Wamh_Template", length = 30)
    private String wamhTemplate;

    @Column(name = "Wamh_Para_List", length = 1000)
    private String wamhParaList;

    @Column(name = "Wamh_C_Date")
    private Date wamhCDate;

    @Column(name = "Wamh_C_User", length = 10)
    private String wamhCUser;

    @Column(name = "Wamh_Batch_Id")
    private String wamhBatchId;

    @Column(name = "Wamh_Bulk_Id")
    private String wamhBulkId;

    @Column(name = "Wamh_Pending_Msg", length = 300)
    private String wamhPendingMsg;

    @Column(name = "Wamh_Pending_Time")
    private Date wamhPendingTime;

    @Column(name = "Wamh_Msg_Count")
    private Integer wamhMsgCount;

    @Column(name = "Wamh_Cost_Pr_Msg")
    private Double wamhCostPrMsg;

    @Column(name = "Wamh_Curr_Code", length = 3)
    private String wamhCurrCode;

    @Column(name = "Wamh_Deliver_Msg", length = 300)
    private String wamhDeliverMsg;

    @Column(name = "Wamh_Deliver_Time")
    private Date wamhDeliverTime;

    @Column(name = "Wamh_Error_Code", length = 50)
    private String wamhErrorCode;

    @Column(name = "Wamh_Error", length = 300)
    private String wamhError;

    @Column(name = "Wamh_Done_At")
    private Date wamhDoneAt;

    @Column(name = "Wamh_Sent_At")
    private Date wamhSentAt;

    @Column(name = "Wamh_Seen_At")
    private Date wamhSeenAt;

    @Column(name = "Wamh_Delete_At")
    private Date wamhDeleteAt;

    @Column(name = "Wamh_Schedule_Id")
    private String wamhScheduleId;

    @Column(name = "Wamh_M_User", length = 10)
    private String wamhMUser;

    @Column(name = "Version")
    private Long version;
}
