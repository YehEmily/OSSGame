import cv2

suits = ["hearts", "clovers", "diamonds", "spades"]
finalheight = 100.0

def imageResizer (imagename):
	image = cv2.imread(imagename)
	r = float(finalheight)/image.shape[1]
	dim = (int(finalheight), int(image.shape[0]*r))
	resized = cv2.resize(image, dim, interpolation = cv2.INTER_AREA)
	cv2.imwrite(imagename, resized)

for i in range(1, 14):
	for j in range(0, 4):
		imagename = suits[j] + str(i) + ".png"
		imageResizer(imagename)

imageResizer("mystery.png")
