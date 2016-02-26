jQuery.fn.rotate = function(angle,whence) {
	var p = this.get(0);
	// we store the angle inside the image tag for persistence
	if (!whence) {
		p.angle = ((p.angle==undefined?0:p.angle) + angle) % 360;
	} else {
		p.angle = angle;
	}

	if (p.angle >= 0) {
		var rotation = Math.PI * p.angle / 180;
	} else {
		var rotation = Math.PI * (360+p.angle) / 180;
	}

	var costheta = Math.round(Math.cos(rotation) * 1000) / 1000;
	var sintheta = Math.round(Math.sin(rotation) * 1000) / 1000;

	var n = (angle%360)/90;
		n = n < 0 ? n + 4 : n;

	if ("filters" in document.createElement("div")&& $.browser.version!=9.0) {
		p.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+n+')';
		//HACK FOR MSIE 8
		switch(n){
			case 0:
			case 2:
				p.parentNode.style.height = p.height;
				break;
			case 1:
			case 3:
				p.parentNode.style.height = p.width;
				break;
		}
		
		return;
	} else {
		var canvas = document.createElement('canvas');
		if (!p.oImage) {
			canvas.oImage = new Image();
			canvas.oImage.src = p.src;
		} else {
			canvas.oImage = p.oImage;
		}
		canvas.style.width = canvas.width = Math.abs(costheta*canvas.oImage.width) + Math.abs(sintheta*canvas.oImage.height);
		canvas.style.height = canvas.height = Math.abs(costheta*canvas.oImage.height) + Math.abs(sintheta*canvas.oImage.width);
		if(canvas.oImage.width == 0||canvas.oImage.height == 0){
			errorBox.show("本张图片尚未完全加载,请稍后再试~")
			return false;
		}
		
		var context = canvas.getContext('2d');
		context.save();

		switch(n) {
				case 0 :
					canvas.setAttribute('width', canvas.oImage.width);
					canvas.setAttribute('height', canvas.oImage.height);
					context.rotate(0 * Math.PI / 180);
					context.drawImage(canvas.oImage, 0, 0);
					break;
				case 1 :
					canvas.setAttribute('width', canvas.oImage.height);
					canvas.setAttribute('height', canvas.oImage.width);
					context.rotate(90 * Math.PI / 180);
					context.drawImage(canvas.oImage, 0, -canvas.oImage.height);
					break;
				case 2 :
					canvas.setAttribute('width', canvas.oImage.width);
					canvas.setAttribute('height', canvas.oImage.height);
					context.rotate(180 * Math.PI / 180);
					context.drawImage(canvas.oImage, -canvas.oImage.width, -canvas.oImage.height);
					break;
				case 3 :
					canvas.setAttribute('width', canvas.oImage.height);
					canvas.setAttribute('height', canvas.oImage.width);
					context.rotate(270 * Math.PI / 180);
					context.drawImage(canvas.oImage, -canvas.oImage.width, 0);
					break;
			}	
		context.restore();
		canvas.id = p.id;
		canvas.angle = p.angle;
		p.parentNode.replaceChild(canvas, p);
	}
}

jQuery.fn.rotateRight = function(angle) {

	this.rotate(angle==undefined?90:angle);
}

jQuery.fn.rotateLeft = function(angle) {
	this.rotate(angle==undefined?-90:-angle);
}
