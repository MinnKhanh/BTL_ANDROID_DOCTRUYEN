<?php

   $connect=mysqli_connect('localhost','root','','doctruyen');
   mysqli_set_charset($connect,'utf8');

  $taikhoan=$_GET["taikhoan"];
  $matkhau=$_GET["matkhau"];
  $sql="SELECT * from taikhoan WHERE TaiKhoan = '$taikhoan' and MatKhau = '$matkhau'";
  $result=mysqli_query($connect,$sql);
  $number_rows=mysqli_num_rows($result);
  if($number_rows==1){
  $each=mysqli_fetch_array($result);
  $array=[];
  $array['taikhoan']=$each['TaiKhoan'];
  $array['matkhau']=$each['MatKhau'];
  $array['pers']=$each['permission'];
  }else{
    $array['taikhoan']='';
    $array['matkhau']='';
    $array['pers']='';
  }
  header('Content-Type: application/json');
  echo json_encode($array);

