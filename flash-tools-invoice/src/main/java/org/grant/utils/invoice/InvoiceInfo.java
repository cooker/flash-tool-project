package org.grant.utils.invoice;

import lombok.Data;

import java.math.BigDecimal;

/**
 * grant
 * 2/9/2019 11:27 AM
 * 描述：
 */
@Data
public class InvoiceInfo {
    //购方信息
    private String purchaserName;
    private String purchaserTaxNo;
    private String purchaserAddr;
    private String purchaserTel;
    private String purchaserAddrAndTel;

    private String purchaserBank;
    private String purchaserBankNo;
    private String purchaserBankAndNo;

    //销方信息
    private String sellerName;
    private String sellerTaxNo;
    private String sellerAddr;
    private String sellerTel;
    private String sellerAddrAndTel;

    private String sellerBank;
    private String sellerBankNo;
    private String sellerBankAndNo;

    private String invoiceNo;
    private String invoiceCode;
    private String invoiceType;
    //开票日期 格式：yyyy-MM-dd
    private String kprq;
    //密码区
    private String secretArea;

    //校验码
    private String checkCode;

    //含税金额
    private String hsje;
    //不含税金额
    private String bhsje;
    //税额
    private String se;
    //备注
    private String remark;

    //收款人
    private String skr;
    //复核人
    private String fhr;
    //开票人
    private String kpr;
}
