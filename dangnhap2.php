<?php
$connect=mysqli_connect('localhost','root','','doctruyen');
$taikhoan=$_GET["taikhoan"];
$matkhau=$_GET["matkhau"];
$sql="select * from taikhoan where TaiKhoan='$taikhoan' and MatKhau='$matkhau'";
$result=mysqli_query($connect,$sql);
$each=mysqli_fetch_array($result);
$dangnhap=[];
$dangnhap['taikhoan']=$each['TaiKhoan'];
$dangnhap['matkhau']=$each['MatKhau'];
    header('Content-Type: application/json');
    echo json_encode($dangnhap);