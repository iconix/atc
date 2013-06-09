namespace :db do
  task :everything => [:environment, :drop, :create, :migrate, :admins] do
    desc "Recreate everything from scratch including pre-populated data"
  end
end
