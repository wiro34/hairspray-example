package com.github.wiro34.hairspray.example.post;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import com.github.wiro34.hairspray.example.ds.user.User;
import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = -2416931054370048135L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    private String subject;

    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
