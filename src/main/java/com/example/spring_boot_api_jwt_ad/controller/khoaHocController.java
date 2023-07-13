package com.example.spring_boot_api_jwt_ad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot_api_jwt_ad.entity.KhoaHoc;
import com.example.spring_boot_api_jwt_ad.service.khoaHocService;

import redis.clients.jedis.Connection;


import redis.clients.jedis.Jedis;


@CrossOrigin("*")

@RequestMapping("/khoahoc")
@RestController
public class khoaHocController {

    
	@Autowired
	khoaHocService service;
	@GetMapping()
	public List<KhoaHoc> findAll() {
		return service.findAll();
	}
	@GetMapping("{maKhoaHoc}")
	
	public KhoaHoc getOne(@PathVariable("maKhoaHoc") Integer maKhoaHoc) {
		
		return service.findmaKhoaHocBy(maKhoaHoc);
	}
	@PostMapping("/create")
	public KhoaHoc create(@RequestBody KhoaHoc auth) {
		
		Jedis jedis = new Jedis("redis://default:C98yuZu6LgZa0yR67fN9b2c8IEZAUVhE@redis-16530.c16.us-east-1-3.ec2.cloud.redislabs.com:16530");
	    Connection connection = jedis.getConnection();
		String formattedMessage ="name khoa hoc:"+auth.getNameKhoaHoc() +" gia:"+ auth.getGia()+" mã khoa hoc:"+auth.getMaKhoaHoc()+" noi dung khoa hoc:"+auth.getNoiDung()+" tac gia:"+auth.getTacGia() + "\n";  
	    jedis.append("khoahocRedis", formattedMessage);
		return service.saveKhoaHoc(auth);
		
	}
	
	@DeleteMapping("{maKhoaHoc}")
	public void delete(@PathVariable("maKhoaHoc") Integer id) {
		 service.deleteByid(id);;
	}
}
