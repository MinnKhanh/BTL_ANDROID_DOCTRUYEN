<?php
 
   $connect=mysqli_connect('localhost','root','','doctruyen');
   mysqli_set_charset($connect,'utf8');
   $taikhoan=$_GET["taikhoan"];
   $matkhau=$_GET["matkhau"];
   $pers=$_GET["pers"];
   $newtaikhoan=$_GET["newtaikhoan"];
   $newmatkhau=$_GET["newmatkhau"];
   $array=[];
    $sql="UPDATE taikhoan SET TaiKhoan='$newtaikhoan',MatKhau='$newmatkhau',permission=$pers WHERE TaiKhoan='$taikhoan' and MatKhau='$matkhau'";
    $result=mysqli_query($connect,$sql);
    $array['taikhoan']=$newtaikhoan;
    $array['matkhau']=$newmatkhau;
    $array['pers']=$pers;
    header('Content-Type: application/json');
    echo json_encode($array);