package me.diego.pokedex;

import me.diego.pokedex.enums.RegionName;
import me.diego.pokedex.enums.RoleName;
import me.diego.pokedex.model.Region;
import me.diego.pokedex.model.Role;
import me.diego.pokedex.repository.RegionRepository;
import me.diego.pokedex.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }
//
//    @GetMapping(path = "/region")
//    public void save() {
//        Region hoenn = new Region();
//        hoenn.setRegionName(RegionName.HOENN);
//
//        regionRepository.save(hoenn);
//
//        Region johto = new Region();
//        johto.setRegionName(RegionName.JOHTO);
//
//        regionRepository.save(johto);
//
//        Region kanto = new Region();
//        kanto.setRegionName(RegionName.KANTO);
//
//        regionRepository.save(kanto);
//
//        Region sinnoh = new Region();
//        sinnoh.setRegionName(RegionName.SINNOH);
//
//        regionRepository.save(sinnoh);
//    }
//
//    @GetMapping(path = "/role")
//    public void saveRole() {
//        Role user = new Role();
//        user.setRoleName(RoleName.ROLE_USER);
//
//        roleRepository.save(user);
//
//        Role admin = new Role();
//        admin.setRoleName(RoleName.ROLE_ADMIN);
//        roleRepository.save(admin);
//    }
}
