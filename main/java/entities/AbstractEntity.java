package entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
@Data
@MappedSuperclass

public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
     @Column(name="creat_At")
    private LocalDateTime creatAt;
    @Column(name="update_At")
    private LocalDateTime updateAt;
    @Column(name = "user_Create")
    private String userCreate;
    @Column(name = "user_Update")
    private String userUpdate;
    @PrePersist
    public void onPrePersit(){
        this.setCreatAt(LocalDateTime.now());
        this.setUpdateAt(LocalDateTime.now());
    }
    @PreUpdate  
    public void onPreUpdate(){
        this.setUpdateAt(LocalDateTime.now());
    }


public void generator(){

}
}