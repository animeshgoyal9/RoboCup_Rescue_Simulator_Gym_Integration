from setuptools import setup

setup(name='RCRS_gym',
      version='0.0.1',
      install_requires=['gym>=0.2.3']
)

setup(name='multiagent',
      version='0.0.1',
      description='Multi-Agent Goal-Driven Communication Environment',
      url='https://github.com/openai/multiagent-public',
      author='Igor Mordatch',
      author_email='mordatch@openai.com',
      packages=find_packages(),
      include_package_data=True,
      zip_safe=False,
      install_requires=['gym', 'numpy-stl']
)