import React from 'react';
// import Wish from './Wish';

const Countdown = ({ countdownData }) => {
  
    return (
      <div>
        <h1 className='heading'>
          오픈까지....
        </h1>
        <div className='countdown-wrapper'>
          <div className='countdown-box'>
            {countdownData.days} 일
            {/* <span className='legend'>일</span> */}
          </div>
          <div className='countdown-box'>
            {countdownData.hours} 시간
            {/* <span className='legend'>시간</span> */}
          </div>
          <div className='countdown-box'>
            {countdownData.minutes} 분
            {/* <span className='legend'>분</span> */}
          </div>
          <div className='countdown-box'>
            {countdownData.seconds} 초
            {/* <span className='legend'>초</span> */}
          </div>
        </div>
      </div>
    );
};

export default Countdown;
