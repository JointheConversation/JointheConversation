// "use strict";
// var date;
// var display = document.getElementById('time');
//
// $(document).ready(function() {
//     getTime('GMT', function(time){
//         date = new Date(time);
//     });
// });
//
// setInterval(function() {
//     date = new Date(date.getTime() + 1000);
//
//     var currenthours = date.getHours();
//     var currentSecs = date.getSeconds();
//     var hours;
//     var minutes;
//     var seconds;
//     if (currenthours == 23 && currentsecs == 0){
//         display.innerHTML = 'LIVE NOW';
//     } else {
//         if (currenthours < 23) {
//             hours = 22 - currenthours;
//         } else {
//             hours = 23;
//         }
//         minutes = 60 - date.getMinutes();
//         seconds = 60 - date.getSeconds();
//
//         if (minutes < 10) {
//             minutes = '0' + minutes;
//         }
//         if (seconds < 10) {
//             seconds = '0' + seconds;
//         }
//         display.innerHTML = hours + ':' + minutes + ':' +seconds;
//     }
// }, 1000);



"use strict";

function getTimeRemaining(endtime) {
    var t = Date.parse(endtime) - Date.parse(new Date());
    var seconds = Math.floor((t / 1000) % 60);
    var minutes = Math.floor((t / 1000 / 60) % 60);
    var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
    return {
        'total': t,
        'hours': hours,
        'minutes': minutes,
        'seconds': seconds
    };
}

function initializeClock(id, endtime) {
    var clock = document.getElementById(id);
    var hoursSpan = clock.querySelector('.hours');
    var minutesSpan = clock.querySelector('.minutes');
    var secondsSpan = clock.querySelector('.seconds');

    function updateClock() {
        var t = getTimeRemaining(endtime);

        hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
        minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
        secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);

        if (t.total <= 0) {
            clearInterval(timeinterval);
        }
    }

    updateClock();
    var timeinterval = setInterval(updateClock, 1000);
}

var deadline = new Date(Date.parse(new Date()) + 24 * 60 * 60 * 1000);
initializeClock('clockdiv', deadline);