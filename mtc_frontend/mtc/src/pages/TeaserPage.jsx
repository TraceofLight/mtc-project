import "./style/TeaserPage.css";
import {useEffect} from 'react';
import stc from "../assets/images/1.png";
import ptc from "../assets/images/2.png";
import htc from "../assets/images/3.png";
import mtc from "../assets/images/4.png";
import $ from 'jquery';
import Dday from "../components/TeaserComponents/Dday";

const TeaserPage = () => {

  const currentDate = new Date();
  const year =
    currentDate.getMonth() === 11 && currentDate.getDate() > 23
      ? currentDate.getFullYear() + 1
      : currentDate.getFullYear();

  useEffect(() =>{
    $(".text .line span").on('click', function(){

      $(".img .front, .img .back").css("transform", "scale(.3)");
      let imgSrc = $(this).attr("data-src");

      setTimeout(function(){
        $(".img .front, .img .back").css("transform", "scale(1)");
        $(".img .front, .img .back").attr("src", imgSrc);
      }, 700);
    });
  });
  

  return (
    <div class="wrapper">
      <Dday />

        <div class="content">
            <div class="img">
                <img class="front" src={mtc} alt="1번" />
                <img class="back" src={mtc} alt="1번" />
            </div>

            <div class="text">
                <div class="line">
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                </div>
                <div class="line line2">
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                </div>
                 <div class="line line3">
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                    <span data-src={stc}>ㅅㅌㅊ</span><span data-src={ptc}>ㅍㅌㅊ</span>
                </div>
                <div class="line line2">
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                    <span data-src={htc}>ㅎㅌㅊ</span><span data-src={mtc}>ㅁㅌㅊ</span>
                </div>
            </div>
            <div class="bottom">
                <h4>TTT's MTC Project</h4>
            </div>
        </div>
    </div>
  );
};

export default TeaserPage;