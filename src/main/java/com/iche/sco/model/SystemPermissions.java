package com.iche.sco.model;

import com.iche.sco.enums.Permissions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "system_permission_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SystemPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "permission_name", unique = true)
    private String name;
    @Column(name = "permission_description", unique = true)
    private String description;
    @Enumerated(EnumType.STRING)
    private Permissions permissions;

}
