package com.xdlr.temperaturerecord.controller;

import com.xdlr.temperaturerecord.Constants;
import com.xdlr.temperaturerecord.model.TemperatureRecord;
import com.xdlr.temperaturerecord.model.UserRegister;
import com.xdlr.temperaturerecord.repo.TemperatureRecordRepository;
import com.xdlr.temperaturerecord.repo.UserRegisterRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Controller
@RequestMapping(path = "/data")
public class UserController {
    private static final Log logger = LogFactory.getLog(SpringApplication.class);

    public static final int SAVE_CREATE = 0;
    public static final int SAVE_UPDATE = 1;

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @Autowired
    private TemperatureRecordRepository temperatureRecordRepository;

    @PostMapping(path = "/user/register") // Map ONLY POST Requests
    public @ResponseBody
    String registerNewUser(
            @RequestParam String idNo, @RequestParam String name, @RequestParam String phone,
            @RequestParam String company, @RequestParam String companyAddress,
            @RequestParam(required = false, defaultValue = "false") boolean toHubei,
            @RequestParam(required = false, defaultValue = "false") boolean toWuhan,
            @RequestParam(required = false, defaultValue = "false") boolean isFever,
            @RequestParam(required = false, defaultValue = "false") boolean isNewCoronavirus) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return save(SAVE_CREATE, idNo, name, phone, company, companyAddress, toHubei, toWuhan, isFever, isNewCoronavirus);
    }

    @PostMapping(path = "/user/edit")
    public @ResponseBody
    String editUser(
            @RequestParam String idNo, @RequestParam String name, @RequestParam String phone,
            @RequestParam String company, @RequestParam String companyAddress,
            @RequestParam(required = false, defaultValue = "false") boolean toHubei,
            @RequestParam(required = false, defaultValue = "false") boolean toWuhan,
            @RequestParam(required = false, defaultValue = "false") boolean isFever,
            @RequestParam(required = false, defaultValue = "false") boolean isNewCoronavirus) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return save(SAVE_UPDATE, idNo, name, phone, company, companyAddress, toHubei, toWuhan, isFever, isNewCoronavirus);
    }

    private String save(int createOrUpdate, String idNo, String name, String phone,
                        String company, String companyAddress, boolean toHubei, boolean toWuhan,
                        boolean isFever, boolean isNewCoronavirus) {
        UserRegister userRegister = userRegisterRepository.findFirstByIdNo(idNo);

        if (createOrUpdate == SAVE_CREATE && userRegister != null) {
            return Constants.MSG_USER_EXISTS;
        }

        if (createOrUpdate == SAVE_UPDATE && userRegister == null) {
            return Constants.MSG_USER_NOT_EXISTS;
        }

        UserRegister saved = new UserRegister();
        if (createOrUpdate == SAVE_CREATE) {
            saved.setRegisterTime(Calendar.getInstance().getTime());
        } else if (createOrUpdate == SAVE_UPDATE) {
            saved.setId(userRegister.getId());
            saved.setRegisterTime(userRegister.getRegisterTime());
        }
        saved.setIdNo(idNo);
        saved.setName(name);
        saved.setPhone(phone);
        saved.setCompany(company);
        saved.setCompanyAddress(companyAddress);
        saved.setToHubei(toHubei);
        saved.setToWuhan(toWuhan);
        saved.setFever(isFever);
        saved.setNewCoronavirus(isNewCoronavirus);
        userRegisterRepository.save(saved);
        return Constants.MSG_SAVED;
    }

    @GetMapping(path = "/user/all")
    public @ResponseBody
    Iterable<UserRegister> getAllUserRegisters() {
        // This returns a JSON or XML with the users
        return userRegisterRepository.findAll();
    }

    @GetMapping(path = "/user")
    public @ResponseBody
    UserRegister getUserRegister(@RequestParam String idNo) {
        // This returns a JSON or XML with the users
        return userRegisterRepository.findFirstByIdNo(idNo);
    }

    @PostMapping(path = "/user/temperature/record")
    public @ResponseBody
    String recordUserTemperature(
            @RequestParam String idNo, @RequestParam String temperature) {
        UserRegister userRegister = userRegisterRepository.findFirstByIdNo(idNo);
        if (userRegister == null) {
            return Constants.MSG_USER_NOT_EXISTS;
        }

        TemperatureRecord saved = new TemperatureRecord();
        saved.setUserRegister(userRegister);
        saved.setTemperature(temperature + "°C");
        saved.setMeasurementTime(Calendar.getInstance().getTime());
        temperatureRecordRepository.save(saved);
        return Constants.MSG_SAVED;
    }
}
