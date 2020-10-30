package io.ron.demo.unified.result.dto;

import javax.validation.constraints.NotBlank;

public class AddressDTO {

    @NotBlank(message = "所在省份不能为空")
    private String province;

    @NotBlank(message = "所在城市不能为空")
    private String city;

    @NotBlank(message = "详细地址不能为空")
    private String detail;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
