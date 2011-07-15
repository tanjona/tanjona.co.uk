/* List Ticker by Alex Fish 
// www.alexefish.com
//
// options:
//
// effect: fade/slide
// speed: milliseconds
// run_once: boolean
*/

(function($){
  $.fn.list_ticker = function(options){
    
    var defaults = {
      speed:4000,
	  effect:'slide',
	  run_once:false
    };
    
    var options = $.extend(defaults, options);
    
    return this.each(function(){
      
      var obj = $(this);
      var list = obj.children();
      var count = list.length - 1;

      list.not(':first').hide();
      
      var interval = setInterval(function(){
        
        list = obj.children();
        list.not(':first').hide();
        
        var first_li = list.eq(0)
        var second_li = list.eq(1)
		
		if(options.effect == 'slide'){
			first_li.slideUp();
			second_li.slideDown(function(){
				first_li.remove().appendTo(obj);
				
			});
		} else if(options.effect == 'fade'){
			first_li.fadeOut(function(){
				obj.css('height',second_li.height());
				second_li.fadeIn();
				first_li.remove().appendTo(obj);
			});
		}
		
		count--;
		
		if(count == 0 && options.run_once){
			clearInterval(interval);
		}
		
      }, options.speed)
    });
  };
})(jQuery);