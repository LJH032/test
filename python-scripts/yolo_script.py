# yolo_script.py
import sys
import torch

def main(image_path):
    model = torch.hub.load('ultralytics/yolov5', 'custom', path='path/to/your_model.pt')
    results = model(image_path)
    results.print()
    detected_objects = results.pandas().xyxy[0].to_json(orient="records")
    print(detected_objects)

if __name__ == "__main__":
    main(sys.argv[1])
