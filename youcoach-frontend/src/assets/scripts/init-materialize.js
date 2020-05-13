(function ($) {
  $(document).ready(
    function () {
      waitForElementToDisplay(function () {
        M.AutoInit();
      });
    }); // end of document ready
})(jQuery); // end of jQuery name space

function waitForElementToDisplay(callBack) {
  if (document.querySelector('app-home') != null) {
    callBack();
    return;
  }
  setTimeout(function () {
    waitForElementToDisplay(callBack);
  }, 10);
}
