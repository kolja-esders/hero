import torch
from PIL import Image
from server.core.helpers.image_cap.build_vocab import load_vocab
from server.core.helpers.image_cap.model import EncoderCNN, DecoderRNN
from torch.autograd import Variable
from torchvision import transforms

from server.core.helpers.utils import PROJ_PATH


def to_var(x, volatile=False):
    if torch.cuda.is_available():
        x = x.cuda()
    return Variable(x, volatile=volatile)

def load_image(image_path, transform=None):
    image = Image.open(image_path)
    image = image.resize([224, 224], Image.LANCZOS)
    
    if transform is not None:
        image = transform(image).unsqueeze(0)
    
    return image
    
def get_img_description(image_path):


    encoder_path = PROJ_PATH+"/helpers/image_cap/models/encoder-5-3000.pkl"
    decoder_path = PROJ_PATH+"/helpers/image_cap/models/decoder-5-3000.pkl"
    embed_size = 256
    hidden_size = 512
    num_layers = 1

    # Image preprocessing
    transform = transforms.Compose([
        transforms.ToTensor(), 
        transforms.Normalize((0.485, 0.456, 0.406), 
                             (0.229, 0.224, 0.225))])

    vocab_path = PROJ_PATH + "/helpers/image_cap/data/"
    vocab = load_vocab(vocab_path)

    # Build Models
    encoder = EncoderCNN(embed_size)
    encoder.eval()  # evaluation mode (BN uses moving mean/variance)
    decoder = DecoderRNN(embed_size, hidden_size,
                         len(vocab), num_layers)
    

    # Load the trained model parameters
    encoder.load_state_dict(torch.load(encoder_path))
    decoder.load_state_dict(torch.load(decoder_path))

    # Prepare Image
    image = load_image(image_path, transform)
    image_tensor = to_var(image, volatile=True)
    
    # If use gpu
    if torch.cuda.is_available():
        encoder.cuda()
        decoder.cuda()
    
    # Generate caption from image
    feature = encoder(image_tensor)
    sampled_ids = decoder.sample(feature)
    sampled_ids = sampled_ids.cpu().numpy()
    
    # Decode word_ids to words
    sampled_caption = []
    for word_id in sampled_ids:
        word = vocab.idx2word[str(word_id)]
        sampled_caption.append(word)
        if word == '<end>':
            break
    sentence = ' '.join(sampled_caption)
    
    # Print out image and generated caption.
    print (sentence)
    return sentence
    
# get_img_description("/home/david/reps/your-insurance/data/cigarette.jpg")

