package com.equipo5.feelflowapp.domain.modules.kudos;

import com.equipo5.feelflowapp.domain.modules.Module;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "kudosmodule_module")
public class KudosModule  extends Module {

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "kudosModule")
    private List<TableBadge> tableBadge = new ArrayList<>();

}

