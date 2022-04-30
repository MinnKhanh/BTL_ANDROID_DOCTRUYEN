<?php
 
   $connect=mysqli_connect('localhost','root','','doctruyen');
   mysqli_set_charset($connect,'utf8');
   $taikhoan=$_GET["taikhoan"];
   $matkhau=$_GET["matkhau"];
   $pers=$_GET["pers"];
   $array=[];
   $sql="select count(*) from taikhoan where TaiKhoan='$taikhoan'";
   $result=mysqli_query($connect,$sql);
   $number_rows=mysqli_fetch_array($result)['count(*)'];
   if($number_rows>=1){
       
        $array['taikhoan']='';
        $array['matkhau']='';
        $array['pers']='';
     
    }
    else{
        $sql="INSERT INTO taikhoan(TaiKhoan,MatKhau,permission) values ('$taikhoan','$matkhau',$pers)";
        $result=mysqli_query($connect,$sql);
        $array['taikhoan']=$taikhoan;
        $array['matkhau']=$matkhau;
        $array['pers']=$pers;

    }
    header('Content-Type: application/json');
    echo json_encode($array);