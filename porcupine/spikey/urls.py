from django.conf.urls import url, include

from . import views

urlpatterns = [
    url(r'login/', views.user_login, name='login'),
    url(r'^loc/', views.location, name='loc'),
    url(r'^msm/', views.msmness, name='msm'),
    url(r'^rank/', views.ranking, name='rank'),
    url(r'^msm_image/', views.msm_image, name='msm_image'),
]