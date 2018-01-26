import time

from PIL import Image
from server.core.helpers.image_det.darknet import Darknet
from server.core.helpers.image_det.utils import *

from server.core.helpers.utils import PROJ_PATH


def detect(imgfile):
    cfgfile = PROJ_PATH+"/helpers/image_det/cfg2/yolo.cfg"
    weightfile = PROJ_PATH+"/helpers/image_det/yolo.weights"

    m = Darknet(cfgfile)

    m.print_network()
    m.load_weights(weightfile)
    print('Loading weights from %s... Done!' % (weightfile))

    namesfile = PROJ_PATH+'/helpers/image_det/data/coco.names'

    use_cuda = 0
    if use_cuda:
        m.cuda()

    img = Image.open(imgfile).convert('RGB')
    sized = img.resize((m.width, m.height))

    for i in range(2):
        start = time.time()
        boxes = do_detect(m, sized, 0.4, 0.4, use_cuda)
        finish = time.time()
        if i == 1:
            print('%s: Predicted in %f seconds.' % (imgfile, (finish - start)))

    class_names = load_class_names(namesfile)

    detections = []

    for box in boxes:
        detections.append(dict(
            type=class_names[box[6]],
            bbox=box[0:4]
        ))

    return detections


    # plot_boxes(img, boxes, 'predictions.jpg', class_names)


if __name__ == '__main__':
    # "cfg/yolo.cfg yolo.weights data/dog.jpg"

    imgfile = "/home/david/reps/your-insurance/data/room.jpg"
    detect(imgfile)
    # detect_cv2(cfgfile, weightfile, imgfile)
    # detect_skimage(cfgfile, weightfile, imgfile)
