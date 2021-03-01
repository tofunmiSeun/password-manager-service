package com.tofunmi.passwordmanager.device;

import com.tofunmi.passwordmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created By tofunmi on 16/12/2020
 */
public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findAllByUser(User user);
}
