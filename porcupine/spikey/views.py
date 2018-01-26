import tempfile
import time

import math

from io import BufferedWriter, FileIO

import numpy as np
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render

# Create your views here.
from django.views.decorators.csrf import csrf_exempt

from helpers.fb.scraper import get_fb_email, get_fb_name, get_profile_pic
from helpers.matching.similarity import get_most_similar_star
from helpers.model.user import create_plain_user
from helpers.msm_analysis.msm_calc import get_mia_san_mia_photo_score, get_mia_san_mia_photo_score_local
from helpers.sap.user_managment import get_user, update_user

active_users = {}
name_list_orig = list(np.random.permutation(["Blondell Fegley","Cassi Friese","Edith Zahler","Roscoe Saidi","Efrain Colley","Edyth Bakke","Gerardo Millhouse","Loise Murtha","Akiko Lazzaro","Dusti Justice","Beryl Gillett","Noriko Fielden","Eleonor Creagh","Elanor Lebo","Antonetta Wemmer","Vergie Spikes","Veola Corpus","Marya Eckard","Yuko Sieck","Corina Kamaka"]))
name_list = list(np.random.permutation(["Blondell Fegley","Cassi Friese","Edith Zahler","Roscoe Saidi","Efrain Colley","Edyth Bakke","Gerardo Millhouse","Loise Murtha","Akiko Lazzaro","Dusti Justice","Beryl Gillett","Noriko Fielden","Eleonor Creagh","Elanor Lebo","Antonetta Wemmer","Vergie Spikes","Veola Corpus","Marya Eckard","Yuko Sieck","Corina Kamaka"]))


def user_login(request):
    # print(request.readlines())
    if request.method == 'GET':
        if len(request.GET.keys()) > 0:
            if "token" in request.GET.keys():

                try:
                    f_auth = request.GET['token']

                    email = get_fb_email(id="me", access_token=f_auth)
                    user, u_pid = get_user(email)
                    user["fb_token"] = f_auth

                    if user["profile_id"] == "":
                        name = get_fb_name(id="me", access_token=f_auth)
                        profile_pic = get_profile_pic(id="me", access_token=f_auth)

                        sim_star, img_url, f_score, uni_s = get_most_similar_star(f_auth)

                        user["profile_id"] = u_pid
                        user["name"] = name
                        user["profile_pic"] = profile_pic
                        user["star"]["star_name"] = sim_star
                        user["star"]["face_sim"] = f_score
                        user["star"]["post_sim"] = uni_s
                        user["star"]["url"] = img_url

                        import numpy as np
                        user["skills"]["motivation"] = np.random.randint(0, 15)
                        user["skills"]["tactics"] = np.random.randint(0, 15)
                        user["skills"]["endurance"] = np.random.randint(0, 15)
                        user["skills"]["teamplay"] = np.random.randint(0, 15)
                        user["skills"]["style"] = np.random.randint(0, 15)

                        print("Updated")

                        update_user(user, u_pid)

                    # name_list = list(np.random.permutation(name_list_orig))
                    # i = np.random.randint(2, 6)
                    # name_list[i] = name

                    update_user({"fb_token": f_auth}, u_pid)

                    print(user)

                    return JsonResponse(user)

                except:
                    return JsonResponse(create_plain_user())

            else:
                return HttpResponse(";-P")

                # f_auth = "".join(request.GET.keys())
                #
                # return HttpResponse("Most Similar Star: " + sim_star + " . <br><br> Face matching: "+ str(f_score) +" <br><br>Common Stuff " + " , ".join(uni_s))
                # return JsonResponse({'name': sim_star, 'img_url' : img_url, 'f_score': f_score, 'uni_s' : list(uni_s)})
        else:
            return HttpResponse(":)")
            #    return HttpResponse("Hello, world. You're at the polls index.")


def location(request):
    if request.method == 'GET':
        if len(request.GET.keys()) > 0:
            if "token" in request.GET.keys() and "lat" in request.GET.keys() and "lng" in request.GET.keys():
                try:

                    print(active_users)

                    f_auth = request.GET['token']
                    lat = request.GET['lat']
                    lng = request.GET['lng']

                    email = get_fb_email(id="me", access_token=f_auth)
                    user, _ = get_user(email)

                    nearby = []

                    active_users[f_auth] = (time.time(), lat, lng, user)

                    for f_id, x in active_users.items():
                        if x:
                            u_time, u_lat, u_lng, u_user = x

                            if u_user.name == user.name:
                                continue

                            if time.time() - u_time > 60 * 10:
                                active_users[f_id] = None
                                continue

                            if True or math.sqrt((u_lat - lat) ** 2 + (u_lng - lng) ** 2) < 0.1:
                                nearby.append(u_user.name)

                    return JsonResponse({"nearby" : nearby})

                except:
                    return JsonResponse({"nearby" : nearby})
            else:
                return JsonResponse({"nearby": ["John Wanye", "Paulchen Panther"]})
        else:
            return JsonResponse({"nearby": ["John Wanye", "Paulchen Panther"]})
    else:
        return JsonResponse({"nearby": ["John Wanye", "Paulchen Panther"]})

    return None


def msmness(request):
    if request.method == 'GET':
        if len(request.GET.keys()) > 0:
            if "url" in request.GET.keys():
                url = request.GET['url']
                try:
                    msm_score = get_mia_san_mia_photo_score(url)
                except:
                    print("Random!!!!")
                    import random
                    msm_score = random.uniform(0, 1)
                return JsonResponse({"score": msm_score})
            else:
                return HttpResponse("0o")
        else:
            return HttpResponse("0o")
    else:
        return HttpResponse("0o")

    return None



def ranking(request):
    return JsonResponse({"ranking": name_list})

@csrf_exempt
def msm_image(request):
    try:

        id = request.POST['id']
        upload = request.FILES['file']

        fh = tempfile.NamedTemporaryFile(delete=False)
        extension = upload.name.split(".")[1]
        filename = "{}.{}".format(fh.name, extension)

        print(filename)

        with BufferedWriter(FileIO(filename, "w")) as dest:
            for c in upload.chunks():
                dest.write(c)

        msm_score, s_hap, s_face, s_red = get_mia_san_mia_photo_score_local(filename)

        print("Img recieved")

        return JsonResponse({"score": msm_score, "happiness": s_hap, "faces": s_face, "redness": s_red})

    except:

        print("Random!!!!")
        import np.random
        msm_score = np.random.randrange(0, 101)
        s_hap = np.random.randrange(0, 11)
        s_face = np.random.randrange(0, 11)
        s_red = np.random.randrange(0, 11)

        return JsonResponse({"score": msm_score, "happiness": s_hap, "faces": s_face, "redness": s_red})
