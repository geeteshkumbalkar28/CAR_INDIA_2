package com.spring.jwt.entity;



import com.spring.jwt.dto.DocumentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "documents")
public class BidCarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentId", nullable = false)
    private Integer DocumentId;

    @Column(name = "DocumentType", length = 250)
    private String documentType;

    @Column(name = "Documentlink", length = 250)
    private String documentLink;

    @Column(name = "user_userId", nullable = false)
    private Integer userId;

    @Column(name = "doc", length = 250)
    private String doc;
    @Column(name = "doctype", length = 250)
    private String doctype;
    @Column(name = "subtype", length = 250)
    private String subtype;
    @Column(name = "comment", length = 250)
    private String comment;

    public BidCarPhoto() {
    }


}